(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('AtividadeSearch', AtividadeSearch);

    AtividadeSearch.$inject = ['$resource'];

    function AtividadeSearch($resource) {
        var resourceUrl =  'api/_search/atividades/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
