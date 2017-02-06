(function() {
    'use strict';

    angular
        .module('autobotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('log-executar-plano', {
            parent: 'entity',
            url: '/log-executar-plano',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.logExecutarPlano.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/log-executar-plano/log-executar-planos.html',
                    controller: 'LogExecutarPlanoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('logExecutarPlano');
                    $translatePartialLoader.addPart('status');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('log-executar-plano-detail', {
            parent: 'entity',
            url: '/log-executar-plano/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.logExecutarPlano.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/log-executar-plano/log-executar-plano-detail.html',
                    controller: 'LogExecutarPlanoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('logExecutarPlano');
                    $translatePartialLoader.addPart('status');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LogExecutarPlano', function($stateParams, LogExecutarPlano) {
                    return LogExecutarPlano.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'log-executar-plano',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('log-executar-plano-detail.edit', {
            parent: 'log-executar-plano-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-executar-plano/log-executar-plano-dialog.html',
                    controller: 'LogExecutarPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LogExecutarPlano', function(LogExecutarPlano) {
                            return LogExecutarPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('log-executar-plano.new', {
            parent: 'log-executar-plano',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-executar-plano/log-executar-plano-dialog.html',
                    controller: 'LogExecutarPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                criado: null,
                                finalizado: null,
                                mensagem: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('log-executar-plano', null, { reload: 'log-executar-plano' });
                }, function() {
                    $state.go('log-executar-plano');
                });
            }]
        })
        .state('log-executar-plano.edit', {
            parent: 'log-executar-plano',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-executar-plano/log-executar-plano-dialog.html',
                    controller: 'LogExecutarPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LogExecutarPlano', function(LogExecutarPlano) {
                            return LogExecutarPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('log-executar-plano', null, { reload: 'log-executar-plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('log-executar-plano.delete', {
            parent: 'log-executar-plano',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-executar-plano/log-executar-plano-delete-dialog.html',
                    controller: 'LogExecutarPlanoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LogExecutarPlano', function(LogExecutarPlano) {
                            return LogExecutarPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('log-executar-plano', null, { reload: 'log-executar-plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
