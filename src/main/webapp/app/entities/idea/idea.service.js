(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('Idea', Idea);

    Idea.$inject = ['$resource', 'DateUtils'];

    function Idea ($resource, DateUtils) {
        var resourceUrl =  'api/ideas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                        data.deadlineDate = DateUtils.convertLocalDateFromServer(data.deadlineDate);
                        data.startWorkDate = DateUtils.convertLocalDateFromServer(data.startWorkDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.deadlineDate = DateUtils.convertLocalDateToServer(copy.deadlineDate);
                    copy.startWorkDate = DateUtils.convertLocalDateToServer(copy.startWorkDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.deadlineDate = DateUtils.convertLocalDateToServer(copy.deadlineDate);
                    copy.startWorkDate = DateUtils.convertLocalDateToServer(copy.startWorkDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
