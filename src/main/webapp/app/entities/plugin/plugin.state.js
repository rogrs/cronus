(function() {
    'use strict';

    angular
        .module('autobotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('plugin', {
            parent: 'entity',
            url: '/plugin',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.plugin.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plugin/plugins.html',
                    controller: 'PluginController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plugin');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('plugin-detail', {
            parent: 'entity',
            url: '/plugin/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autobotApp.plugin.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plugin/plugin-detail.html',
                    controller: 'PluginDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plugin');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Plugin', function($stateParams, Plugin) {
                    return Plugin.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'plugin',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('plugin-detail.edit', {
            parent: 'plugin-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plugin/plugin-dialog.html',
                    controller: 'PluginDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plugin', function(Plugin) {
                            return Plugin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plugin.new', {
            parent: 'plugin',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plugin/plugin-dialog.html',
                    controller: 'PluginDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                comando: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('plugin', null, { reload: 'plugin' });
                }, function() {
                    $state.go('plugin');
                });
            }]
        })
        .state('plugin.edit', {
            parent: 'plugin',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plugin/plugin-dialog.html',
                    controller: 'PluginDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plugin', function(Plugin) {
                            return Plugin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plugin', null, { reload: 'plugin' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plugin.delete', {
            parent: 'plugin',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plugin/plugin-delete-dialog.html',
                    controller: 'PluginDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Plugin', function(Plugin) {
                            return Plugin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plugin', null, { reload: 'plugin' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
