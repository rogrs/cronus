(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ProjetoDetailController', ProjetoDetailController);

    ProjetoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Projeto', 'Plano'];

    function ProjetoDetailController($scope, $rootScope, $stateParams, previousState, entity, Projeto, Plano) {
        var vm = this;

        vm.projeto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:projetoUpdate', function(event, result) {
            vm.projeto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
