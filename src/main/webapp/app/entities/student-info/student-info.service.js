(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('StudentInfo', StudentInfo);

    StudentInfo.$inject = ['$resource'];

    function StudentInfo ($resource) {
        var resourceUrl =  'api/student-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'addSkillToStudent': {
                method: 'PUT',
                url: 'api/student-infos/:id/addSkill'
            },
            'updateSkill': {
                method: 'PUT',
                url: 'api/student-infos/:id/updateSkill'
            },
            'deleteSkill': {
                method: 'PUT',
                url: 'api/student-infos/:id/deleteSkill'
            }
        });
    }
})();
