'use strict';

describe('Controller Tests', function() {

    beforeEach(mockApiAccountCall);
    beforeEach(mockI18nCalls);

    describe('RegisterController', function() {

        var $scope, $q; // actual implementations
        var MockTimeout, MockTranslate, MockAuth, MockFaculty; // mocks
        var createController; // local utility function

        beforeEach(inject(function($injector) {
            $q = $injector.get('$q');
            $scope = $injector.get('$rootScope').$new();
            MockTimeout = jasmine.createSpy('MockTimeout');
            MockAuth = jasmine.createSpyObj('MockAuth', ['createStudentAccount']);
            MockFaculty = jasmine.createSpyObj('MockFaculty', ['query']);
            MockTranslate = jasmine.createSpyObj('MockTranslate', ['use']);

            var locals = {
                'Auth': MockAuth,
                'Faculty' : MockFaculty,
                '$translate': MockTranslate,
                '$timeout': MockTimeout,
                '$scope': $scope
            };
            createController = function() {
                $injector.get('$controller')('RegisterController as vm', locals);
            };
        }));

        it('should ensure the two passwords entered match', function() {
            // given
            createController();
            $scope.vm.registerAccount.password = 'password';
            $scope.vm.confirmPassword = 'non-matching';
            // when
            $scope.vm.register();
            // then
            expect($scope.vm.doNotMatch).toEqual('ERROR');
        });

        it('should update success to OK after creating an account', function() {
            // given
            MockTranslate.use.and.returnValue('ru');
            MockAuth.createStudentAccount.and.returnValue($q.resolve());
            MockFaculty.query.and.returnValue([{'name': 'faculty'}]);
            createController();
            $scope.vm.registerAccount.password = $scope.vm.confirmPassword = 'password';
            $scope.vm.selectedFaculty = {};
            $scope.vm.selectedFaculty.name = 'faculty';
            $scope.vm.selectedDepartment = {};
            $scope.vm.selectedDepartment.name = 'department';
            // when
            $scope.$apply($scope.vm.register); // $q promises require an $apply
            // then
            expect(MockAuth.createStudentAccount).toHaveBeenCalledWith({
                password: 'password',
                langKey: 'ru',
                studentInfo: {
                    faculty: 'faculty',
                    department: 'department'
                }
            });
            expect($scope.vm.success).toEqual('OK');
            expect($scope.vm.registerAccount.langKey).toEqual('ru');
            expect(MockTranslate.use).toHaveBeenCalled();
            expect($scope.vm.errorUserExists).toBeNull();
            expect($scope.vm.errorEmailExists).toBeNull();
            expect($scope.vm.error).toBeNull();
        });

        it('should notify of email existence upon 400/e-mail address already in use', function() {
            // given
            MockAuth.createStudentAccount.and.returnValue($q.reject({
                status: 400,
                data: 'e-mail address already in use'
            }));
            createController();
            $scope.vm.registerAccount.password = $scope.vm.confirmPassword = 'password';
            $scope.vm.selectedFaculty = {};
            $scope.vm.selectedDepartment = {};
            // when
            $scope.$apply($scope.vm.register); // $q promises require an $apply
            // then
            expect($scope.vm.errorEmailExists).toEqual('ERROR');
            expect($scope.vm.errorUserExists).toBeNull();
            expect($scope.vm.error).toBeNull();
        });

        it('should notify of generic error', function() {
            // given
            MockAuth.createStudentAccount.and.returnValue($q.reject({
                status: 503
            }));
            createController();
            $scope.vm.registerAccount.password = $scope.vm.confirmPassword = 'password';
            $scope.vm.selectedFaculty = {};
            $scope.vm.selectedDepartment = {};
            // when
            $scope.$apply($scope.vm.register); // $q promises require an $apply
            // then
            expect($scope.vm.errorUserExists).toBeNull();
            expect($scope.vm.errorEmailExists).toBeNull();
            expect($scope.vm.error).toEqual('ERROR');
        });

    });
});
