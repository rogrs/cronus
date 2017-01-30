(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('Projeto', Projeto);

    Projeto.$inject = ['$resource'];

    function Projeto ($resource) {
        var resourceUrl =  'api/projetos/:id';

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
