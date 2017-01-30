(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('ProjetoSearch', ProjetoSearch);

    ProjetoSearch.$inject = ['$resource'];

    function ProjetoSearch($resource) {
        var resourceUrl =  'api/_search/projetos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
