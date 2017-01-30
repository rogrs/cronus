(function() {
    'use strict';

    angular
        .module('autobotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('script', {
            parent: 'entity',
            url: '/script',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.script.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/script/scripts.html',
                    controller: 'ScriptController',
                    controllerAs: 'vm'
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
        .state('script-detail', {
            parent: 'entity',
            url: '/script/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.script.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/script/script-detail.html',
                    controller: 'ScriptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('script');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Script', function($stateParams, Script) {
                    return Script.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'script',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('script-detail.edit', {
            parent: 'script-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-dialog.html',
                    controller: 'ScriptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Script', function(Script) {
                            return Script.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('script.new', {
            parent: 'script',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-dialog.html',
                    controller: 'ScriptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                path: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('script', null, { reload: 'script' });
                }, function() {
                    $state.go('script');
                });
            }]
        })
        .state('script.edit', {
            parent: 'script',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-dialog.html',
                    controller: 'ScriptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Script', function(Script) {
                            return Script.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('script', null, { reload: 'script' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('script.delete', {
            parent: 'script',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-delete-dialog.html',
                    controller: 'ScriptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Script', function(Script) {
                            return Script.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('script', null, { reload: 'script' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
