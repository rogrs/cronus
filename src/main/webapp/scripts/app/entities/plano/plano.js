'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plano', {
                parent: 'entity',
                url: '/planos',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.plano.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plano/planos.html',
                        controller: 'PlanoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plano');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('plano.detail', {
                parent: 'entity',
                url: '/plano/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.plano.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plano/plano-detail.html',
                        controller: 'PlanoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plano');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Plano', function($stateParams, Plano) {
                        return Plano.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plano.new', {
                parent: 'plano',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plano/plano-dialog.html',
                        controller: 'PlanoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {descricao: null, detalhe: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('plano', null, { reload: true });
                    }, function() {
                        $state.go('plano');
                    })
                }]
            })
            .state('plano.edit', {
                parent: 'plano',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plano/plano-dialog.html',
                        controller: 'PlanoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Plano', function(Plano) {
                                return Plano.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plano', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
