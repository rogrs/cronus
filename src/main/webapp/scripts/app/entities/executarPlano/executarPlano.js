'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('executarPlano', {
                parent: 'entity',
                url: '/executarPlanos',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.executarPlano.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/executarPlano/executarPlanos.html',
                        controller: 'ExecutarPlanoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('executarPlano');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('executarPlano.detail', {
                parent: 'entity',
                url: '/executarPlano/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.executarPlano.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/executarPlano/executarPlano-detail.html',
                        controller: 'ExecutarPlanoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('executarPlano');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ExecutarPlano', function($stateParams, ExecutarPlano) {
                        return ExecutarPlano.get({id : $stateParams.id});
                    }]
                }
            })
            .state('executarPlano.new', {
                parent: 'executarPlano',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/executarPlano/executarPlano-dialog.html',
                        controller: 'ExecutarPlanoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {criado: null, inicio: null, finalizado: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('executarPlano', null, { reload: true });
                    }, function() {
                        $state.go('executarPlano');
                    })
                }]
            })
            .state('executarPlano.edit', {
                parent: 'executarPlano',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/executarPlano/executarPlano-dialog.html',
                        controller: 'ExecutarPlanoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ExecutarPlano', function(ExecutarPlano) {
                                return ExecutarPlano.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('executarPlano', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
