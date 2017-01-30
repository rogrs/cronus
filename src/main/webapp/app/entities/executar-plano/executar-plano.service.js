(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('ExecutarPlano', ExecutarPlano);

    ExecutarPlano.$inject = ['$resource'];

    function ExecutarPlano ($resource) {
        var resourceUrl =  'api/executar-planos/:id';

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
