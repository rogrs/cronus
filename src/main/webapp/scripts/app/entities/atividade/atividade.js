'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('atividade', {
                parent: 'entity',
                url: '/atividades',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.atividade.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/atividade/atividades.html',
                        controller: 'AtividadeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('atividade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('atividade.detail', {
                parent: 'entity',
                url: '/atividade/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.atividade.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/atividade/atividade-detail.html',
                        controller: 'AtividadeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('atividade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Atividade', function($stateParams, Atividade) {
                        return Atividade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('atividade.new', {
                parent: 'atividade',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/atividade/atividade-dialog.html',
                        controller: 'AtividadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {descricao: null, detalhe: null, falha_para: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('atividade', null, { reload: true });
                    }, function() {
                        $state.go('atividade');
                    })
                }]
            })
            .state('atividade.edit', {
                parent: 'atividade',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/atividade/atividade-dialog.html',
                        controller: 'AtividadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Atividade', function(Atividade) {
                                return Atividade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('atividade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
