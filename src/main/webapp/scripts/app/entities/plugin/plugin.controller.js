'use strict';

angular.module('jhipsterApp')
    .controller('PluginController', function ($scope, Plugin, ParseLinks) {
        $scope.plugins = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Plugin.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plugins = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Plugin.get({id: id}, function(result) {
                $scope.plugin = result;
                $('#deletePluginConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plugin.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePluginConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.plugin = {descricao: null, comando: null, id: null};
        };
    });
