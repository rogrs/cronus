'use strict';

angular.module('jhipsterApp')
    .controller('ExecutarPlanoController', function ($scope, ExecutarPlano, ParseLinks) {
        $scope.executarPlanos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ExecutarPlano.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.executarPlanos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ExecutarPlano.get({id: id}, function(result) {
                $scope.executarPlano = result;
                $('#deleteExecutarPlanoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ExecutarPlano.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteExecutarPlanoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.executarPlano = {criado: null, inicio: null, finalizado: null, id: null};
        };
    });
