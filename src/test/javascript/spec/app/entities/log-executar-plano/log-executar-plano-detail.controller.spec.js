'use strict';

describe('Controller Tests', function() {

    describe('LogExecutarPlano Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLogExecutarPlano, MockExecutarPlano;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLogExecutarPlano = jasmine.createSpy('MockLogExecutarPlano');
            MockExecutarPlano = jasmine.createSpy('MockExecutarPlano');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LogExecutarPlano': MockLogExecutarPlano,
                'ExecutarPlano': MockExecutarPlano
            };
            createController = function() {
                $injector.get('$controller')("LogExecutarPlanoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autobotApp:logExecutarPlanoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
