'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('script', {
                parent: 'entity',
                url: '/scripts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.script.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/script/scripts.html',
                        controller: 'ScriptController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('script');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('script.detail', {
                parent: 'entity',
                url: '/script/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.script.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/script/script-detail.html',
                        controller: 'ScriptDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('script');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Script', function($stateParams, Script) {
                        return Script.get({id : $stateParams.id});
                    }]
                }
            })
            .state('script.new', {
                parent: 'script',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/script/script-dialog.html',
                        controller: 'ScriptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {descricao: null, detalhe: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('script', null, { reload: true });
                    }, function() {
                        $state.go('script');
                    })
                }]
            })
            .state('script.edit', {
                parent: 'script',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/script/script-dialog.html',
                        controller: 'ScriptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Script', function(Script) {
                                return Script.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('script', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
