'use strict';

angular.module('jhipsterApp')
    .controller('AtividadeController', function ($scope, Atividade, ParseLinks) {
        $scope.atividades = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Atividade.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.atividades = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Atividade.get({id: id}, function(result) {
                $scope.atividade = result;
                $('#deleteAtividadeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Atividade.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAtividadeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.atividade = {descricao: null, detalhe: null, falha_para: null, id: null};
        };
    });
