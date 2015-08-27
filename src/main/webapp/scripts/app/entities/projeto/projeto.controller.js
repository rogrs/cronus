'use strict';

angular.module('jhipsterApp')
    .controller('ProjetoController', function ($scope, Projeto, ParseLinks) {
        $scope.projetos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Projeto.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.projetos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Projeto.get({id: id}, function(result) {
                $scope.projeto = result;
                $('#deleteProjetoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Projeto.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProjetoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.projeto = {descricao: null, detalhes: null, id: null};
        };
    });
