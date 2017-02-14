(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('Track', Track);

    Track.$inject = ['$resource'];

    function Track ($resource) {
        var resourceUrl =  'api/tracks/:id';

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
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    return angular.toJson(copy);
                }
            },
            'teachers': {
                method: 'GET',
                url: 'api/tracks/:id/teachers',
                isArray: true
            }
        });
    }
})();
