(function() {
    'use strict';

    angular
        .module('autobotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('executar-plano', {
            parent: 'entity',
            url: '/executar-plano',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.executarPlano.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/executar-plano/executar-planos.html',
                    controller: 'ExecutarPlanoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('executarPlano');
                    $translatePartialLoader.addPart('status');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('executar-plano-detail', {
            parent: 'entity',
            url: '/executar-plano/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.executarPlano.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/executar-plano/executar-plano-detail.html',
                    controller: 'ExecutarPlanoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('executarPlano');
                    $translatePartialLoader.addPart('status');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExecutarPlano', function($stateParams, ExecutarPlano) {
                    return ExecutarPlano.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'executar-plano',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('executar-plano-detail.edit', {
            parent: 'executar-plano-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/executar-plano/executar-plano-dialog.html',
                    controller: 'ExecutarPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExecutarPlano', function(ExecutarPlano) {
                            return ExecutarPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('executar-plano.new', {
            parent: 'executar-plano',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/executar-plano/executar-plano-dialog.html',
                    controller: 'ExecutarPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                detalhes: null,
                                mensagem: null,
                                pararNaFalha: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('executar-plano', null, { reload: 'executar-plano' });
                }, function() {
                    $state.go('executar-plano');
                });
            }]
        })
        .state('executar-plano.edit', {
            parent: 'executar-plano',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/executar-plano/executar-plano-dialog.html',
                    controller: 'ExecutarPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExecutarPlano', function(ExecutarPlano) {
                            return ExecutarPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('executar-plano', null, { reload: 'executar-plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('executar-plano.delete', {
            parent: 'executar-plano',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/executar-plano/executar-plano-delete-dialog.html',
                    controller: 'ExecutarPlanoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExecutarPlano', function(ExecutarPlano) {
                            return ExecutarPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('executar-plano', null, { reload: 'executar-plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
