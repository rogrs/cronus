(function() {
    'use strict';

    angular
        .module('autobotApp')
        .factory('PluginSearch', PluginSearch);

    PluginSearch.$inject = ['$resource'];

    function PluginSearch($resource) {
        var resourceUrl =  'api/_search/plugins/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
