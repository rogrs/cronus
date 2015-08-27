'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plugin', {
                parent: 'entity',
                url: '/plugins',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.plugin.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plugin/plugins.html',
                        controller: 'PluginController'
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
            .state('plugin.detail', {
                parent: 'entity',
                url: '/plugin/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.plugin.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plugin/plugin-detail.html',
                        controller: 'PluginDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plugin');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Plugin', function($stateParams, Plugin) {
                        return Plugin.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plugin.new', {
                parent: 'plugin',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plugin/plugin-dialog.html',
                        controller: 'PluginDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {descricao: null, comando: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('plugin', null, { reload: true });
                    }, function() {
                        $state.go('plugin');
                    })
                }]
            })
            .state('plugin.edit', {
                parent: 'plugin',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plugin/plugin-dialog.html',
                        controller: 'PluginDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Plugin', function(Plugin) {
                                return Plugin.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plugin', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
