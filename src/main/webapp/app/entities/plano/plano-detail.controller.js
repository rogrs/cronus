(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PlanoDetailController', PlanoDetailController);

    PlanoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Plano', 'Atividade', 'ExecutarPlano', 'Projeto'];

    function PlanoDetailController($scope, $rootScope, $stateParams, previousState, entity, Plano, Atividade, ExecutarPlano, Projeto) {
        var vm = this;

        vm.plano = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:planoUpdate', function(event, result) {
            vm.plano = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
