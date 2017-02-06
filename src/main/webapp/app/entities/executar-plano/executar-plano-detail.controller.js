(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ExecutarPlanoDetailController', ExecutarPlanoDetailController);

    ExecutarPlanoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExecutarPlano', 'LogExecutarPlano', 'Plano'];

    function ExecutarPlanoDetailController($scope, $rootScope, $stateParams, previousState, entity, ExecutarPlano, LogExecutarPlano, Plano) {
        var vm = this;

        vm.executarPlano = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:executarPlanoUpdate', function(event, result) {
            vm.executarPlano = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
