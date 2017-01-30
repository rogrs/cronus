(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('Script', Script);

    Script.$inject = ['$resource'];

    function Script ($resource) {
        var resourceUrl =  'api/scripts/:id';

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
