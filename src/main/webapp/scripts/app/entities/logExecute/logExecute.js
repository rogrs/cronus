'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logExecute', {
                parent: 'entity',
                url: '/logExecutes',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.logExecute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/logExecute/logExecutes.html',
                        controller: 'LogExecuteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('logExecute');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('logExecute.detail', {
                parent: 'entity',
                url: '/logExecute/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.logExecute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/logExecute/logExecute-detail.html',
                        controller: 'LogExecuteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('logExecute');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LogExecute', function($stateParams, LogExecute) {
                        return LogExecute.get({id : $stateParams.id});
                    }]
                }
            })
            .state('logExecute.new', {
                parent: 'logExecute',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/logExecute/logExecute-dialog.html',
                        controller: 'LogExecuteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {created: null, message: null, hostname: null, login: null, status: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('logExecute', null, { reload: true });
                    }, function() {
                        $state.go('logExecute');
                    })
                }]
            })
            .state('logExecute.edit', {
                parent: 'logExecute',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/logExecute/logExecute-dialog.html',
                        controller: 'LogExecuteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LogExecute', function(LogExecute) {
                                return LogExecute.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('logExecute', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
