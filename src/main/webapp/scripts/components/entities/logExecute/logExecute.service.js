'use strict';

angular.module('jhipsterApp')
    .factory('LogExecute', function ($resource, DateUtils) {
        return $resource('api/logExecutes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.created = DateUtils.convertDateTimeFromServer(data.created);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
