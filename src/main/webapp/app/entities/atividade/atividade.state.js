(function() {
    'use strict';

    angular
        .module('autobotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('atividade', {
            parent: 'entity',
            url: '/atividade',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.atividade.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atividade/atividades.html',
                    controller: 'AtividadeController',
                    controllerAs: 'vm'
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
        .state('atividade-detail', {
            parent: 'entity',
            url: '/atividade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.atividade.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atividade/atividade-detail.html',
                    controller: 'AtividadeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('atividade');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Atividade', function($stateParams, Atividade) {
                    return Atividade.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'atividade',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('atividade-detail.edit', {
            parent: 'atividade-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atividade/atividade-dialog.html',
                    controller: 'AtividadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Atividade', function(Atividade) {
                            return Atividade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('atividade.new', {
            parent: 'atividade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atividade/atividade-dialog.html',
                    controller: 'AtividadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                comando: null,
                                pararNaFalha: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('atividade', null, { reload: 'atividade' });
                }, function() {
                    $state.go('atividade');
                });
            }]
        })
        .state('atividade.edit', {
            parent: 'atividade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atividade/atividade-dialog.html',
                    controller: 'AtividadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Atividade', function(Atividade) {
                            return Atividade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('atividade', null, { reload: 'atividade' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('atividade.delete', {
            parent: 'atividade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atividade/atividade-delete-dialog.html',
                    controller: 'AtividadeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Atividade', function(Atividade) {
                            return Atividade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('atividade', null, { reload: 'atividade' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
