'use strict';

angular.module('jhipsterApp')
    .factory('ExecutarPlano', function ($resource, DateUtils) {
        return $resource('api/executarPlanos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.criado = DateUtils.convertDateTimeFromServer(data.criado);
                    data.inicio = DateUtils.convertDateTimeFromServer(data.inicio);
                    data.finalizado = DateUtils.convertDateTimeFromServer(data.finalizado);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
