(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('LogExecutarPlanoSearch', LogExecutarPlanoSearch);

    LogExecutarPlanoSearch.$inject = ['$resource'];

    function LogExecutarPlanoSearch($resource) {
        var resourceUrl =  'api/_search/log-executar-planos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
