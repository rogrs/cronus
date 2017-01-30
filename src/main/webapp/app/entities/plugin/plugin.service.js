(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('Plugin', Plugin);

    Plugin.$inject = ['$resource'];

    function Plugin ($resource) {
        var resourceUrl =  'api/plugins/:id';

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
