'use strict';

angular.module('jhipsterApp')
    .controller('ScriptController', function ($scope, Script, ParseLinks) {
        $scope.scripts = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Script.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.scripts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Script.get({id: id}, function(result) {
                $scope.script = result;
                $('#deleteScriptConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Script.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteScriptConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.script = {descricao: null, detalhe: null, id: null};
        };
    });
