(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('ScriptSearch', ScriptSearch);

    ScriptSearch.$inject = ['$resource'];

    function ScriptSearch($resource) {
        var resourceUrl =  'api/_search/scripts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
