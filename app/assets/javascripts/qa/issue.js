app.controller('IssueController', function($scope, $http) {
    $scope.issue = {};
    $scope.comm = {};
    $scope.error = false;

    $scope.saveIssue = function() {
			console.log($scope.issue);
        $http.post(Routes.saveIssue, JSON.stringify($scope.issue)).success(function(data, status) {
            alert("Issue has been added into database");
        }).error(function(data, status) {
			$scope.error = true;
        });
    };

    $(function() {
		$("[data-add-issue]").click(function() {
			$scope.error = false;

			$scope.issue = {
				title: "",
				id: 0,
				text: "",
				projectId: projectId,
				status: "Not Solved"
			};
			$scope.$apply();
		});
	});
});
