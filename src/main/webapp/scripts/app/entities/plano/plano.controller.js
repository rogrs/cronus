'use strict';

angular.module('jhipsterApp')
    .controller('PlanoController', function ($scope, Plano, ParseLinks) {
        $scope.planos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Plano.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.planos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Plano.get({id: id}, function(result) {
                $scope.plano = result;
                $('#deletePlanoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plano.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlanoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.plano = {descricao: null, detalhe: null, id: null};
        };
    });
