(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('TrackDefinition', TrackDefinition);

    TrackDefinition.$inject = ['$resource'];

    function TrackDefinition ($resource) {
        var resourceUrl =  'api/track-definitions/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
