(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('Stream', Stream);

    Stream.$inject = ['$resource', 'DateUtils'];

    function Stream ($resource, DateUtils) {
        var resourceUrl =  'api/streams/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'addTrack': {
                method: 'POST',
                url: 'api/streams/:id/tracks'
            },
            'updateTrack': {
                method: 'PUT',
                url: 'api/streams/:id/tracks/:trackId'
            },
            'getTrack': {
                method: 'GET',
                url: 'api/streams/:id/tracks/:trackId'
            },
            'addTeacherToTrack': {
                method: 'PUT',
                url: 'api/streams/:id/tracks/:trackId/addTeacher'
            },
            'deleteTeacher': {
                method: 'DELETE',
                url: 'api/streams/:id/tracks/:trackId/teachers/:teacherId'
            },
            'addSubjectToTrack': {
                method: 'PUT',
                url: 'api/streams/:id/tracks/:trackId/addSubject'
            },
            'deleteTrack': {
                method: 'DELETE',
                url: 'api/streams/:id/tracks/:trackId'
            },
            'deleteSubject': {
                method: 'DELETE',
                url: 'api/streams/:id/tracks/:trackId/subjects/:subjectId'
            },
            'getSubject': {
                method: 'GET',
                url: 'api/streams/:id/tracks/:trackId/subjects/:subjectId'
            },
            'updateSubject': {
                method: 'PUT',
                url: 'api/streams/:id/tracks/:trackId/subjects/:subjectId'
            },
            'addGroup': {
                method: 'POST',
                url: 'api/streams/:id/addGroup'
            }
        });
    }
})();
