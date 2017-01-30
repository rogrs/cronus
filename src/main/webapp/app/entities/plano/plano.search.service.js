(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('PlanoSearch', PlanoSearch);

    PlanoSearch.$inject = ['$resource'];

    function PlanoSearch($resource) {
        var resourceUrl =  'api/_search/planos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
