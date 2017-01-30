(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('Atividade', Atividade);

    Atividade.$inject = ['$resource'];

    function Atividade ($resource) {
        var resourceUrl =  'api/atividades/:id';

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
