(function() {
    'use strict';
    angular
        .module('autobotApp')
        .factory('LogExecutarPlano', LogExecutarPlano);

    LogExecutarPlano.$inject = ['$resource', 'DateUtils'];

    function LogExecutarPlano ($resource, DateUtils) {
        var resourceUrl =  'api/log-executar-planos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.criado = DateUtils.convertLocalDateFromServer(data.criado);
                        data.finalizado = DateUtils.convertLocalDateFromServer(data.finalizado);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.criado = DateUtils.convertLocalDateToServer(copy.criado);
                    copy.finalizado = DateUtils.convertLocalDateToServer(copy.finalizado);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.criado = DateUtils.convertLocalDateToServer(copy.criado);
                    copy.finalizado = DateUtils.convertLocalDateToServer(copy.finalizado);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
