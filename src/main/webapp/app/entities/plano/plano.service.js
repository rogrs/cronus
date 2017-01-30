(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('Plano', Plano);

    Plano.$inject = ['$resource'];

    function Plano ($resource) {
        var resourceUrl =  'api/planos/:id';

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
