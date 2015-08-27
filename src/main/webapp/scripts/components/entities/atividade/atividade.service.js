'use strict';

angular.module('jhipsterApp')
    .factory('Atividade', function ($resource, DateUtils) {
        return $resource('api/atividades/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
