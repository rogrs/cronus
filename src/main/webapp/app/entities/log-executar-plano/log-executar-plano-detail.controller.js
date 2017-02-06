(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('LogExecutarPlanoDetailController', LogExecutarPlanoDetailController);

    LogExecutarPlanoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LogExecutarPlano', 'ExecutarPlano'];

    function LogExecutarPlanoDetailController($scope, $rootScope, $stateParams, previousState, entity, LogExecutarPlano, ExecutarPlano) {
        var vm = this;

        vm.logExecutarPlano = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:logExecutarPlanoUpdate', function(event, result) {
            vm.logExecutarPlano = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
