(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('TeacherInfo', TeacherInfo);

    TeacherInfo.$inject = ['$resource'];

    function TeacherInfo ($resource) {
        let resourceUrl =  'api/teacher-infos/:id';

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
            'find' : {
                url: 'api/teacher-infos/find/:query',
                method:'GET',
                isArray: true
            }
        });
    }
})();
