'use strict';

angular.module('jhipsterApp')
    .controller('LogExecuteController', function ($scope, LogExecute, ParseLinks) {
        $scope.logExecutes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            LogExecute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.logExecutes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            LogExecute.get({id: id}, function(result) {
                $scope.logExecute = result;
                $('#deleteLogExecuteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            LogExecute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLogExecuteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.logExecute = {created: null, message: null, hostname: null, login: null, status: null, id: null};
        };
    });
