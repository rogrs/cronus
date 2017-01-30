(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('ExecutarPlanoSearch', ExecutarPlanoSearch);

    ExecutarPlanoSearch.$inject = ['$resource'];

    function ExecutarPlanoSearch($resource) {
        var resourceUrl =  'api/_search/executar-planos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
