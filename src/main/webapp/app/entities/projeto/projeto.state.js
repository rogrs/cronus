(function() {
    'use strict';

    angular
        .module('autobotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('projeto', {
            parent: 'entity',
            url: '/projeto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.projeto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/projeto/projetos.html',
                    controller: 'ProjetoController',
                    controllerAs: 'vm'
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
        .state('projeto-detail', {
            parent: 'entity',
            url: '/projeto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.projeto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/projeto/projeto-detail.html',
                    controller: 'ProjetoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('projeto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Projeto', function($stateParams, Projeto) {
                    return Projeto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'projeto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('projeto-detail.edit', {
            parent: 'projeto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projeto/projeto-dialog.html',
                    controller: 'ProjetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projeto', function(Projeto) {
                            return Projeto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projeto.new', {
            parent: 'projeto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projeto/projeto-dialog.html',
                    controller: 'ProjetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                detalhes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('projeto', null, { reload: 'projeto' });
                }, function() {
                    $state.go('projeto');
                });
            }]
        })
        .state('projeto.edit', {
            parent: 'projeto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projeto/projeto-dialog.html',
                    controller: 'ProjetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projeto', function(Projeto) {
                            return Projeto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projeto', null, { reload: 'projeto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projeto.delete', {
            parent: 'projeto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projeto/projeto-delete-dialog.html',
                    controller: 'ProjetoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Projeto', function(Projeto) {
                            return Projeto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projeto', null, { reload: 'projeto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
