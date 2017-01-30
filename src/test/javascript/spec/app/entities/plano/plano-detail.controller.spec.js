'use strict';

describe('Controller Tests', function() {

    describe('Plano Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPlano, MockAtividade, MockExecutarPlano, MockProjeto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPlano = jasmine.createSpy('MockPlano');
            MockAtividade = jasmine.createSpy('MockAtividade');
            MockExecutarPlano = jasmine.createSpy('MockExecutarPlano');
            MockProjeto = jasmine.createSpy('MockProjeto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Plano': MockPlano,
                'Atividade': MockAtividade,
                'ExecutarPlano': MockExecutarPlano,
                'Projeto': MockProjeto
            };
            createController = function() {
                $injector.get('$controller')("PlanoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autobotApp:planoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
