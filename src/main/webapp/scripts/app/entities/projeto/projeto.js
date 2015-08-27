'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projeto', {
                parent: 'entity',
                url: '/projetos',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.projeto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projeto/projetos.html',
                        controller: 'ProjetoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projeto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projeto.detail', {
                parent: 'entity',
                url: '/projeto/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.projeto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projeto/projeto-detail.html',
                        controller: 'ProjetoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projeto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Projeto', function($stateParams, Projeto) {
                        return Projeto.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projeto.new', {
                parent: 'projeto',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/projeto/projeto-dialog.html',
                        controller: 'ProjetoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {descricao: null, detalhes: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('projeto', null, { reload: true });
                    }, function() {
                        $state.go('projeto');
                    })
                }]
            })
            .state('projeto.edit', {
                parent: 'projeto',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/projeto/projeto-dialog.html',
                        controller: 'ProjetoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Projeto', function(Projeto) {
                                return Projeto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projeto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
