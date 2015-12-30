app.controller('IssueController', function($scope, $http) {
    $scope.issue = {};
    $scope.saveIssue = function() {
        $http.post(Routes.saveIssue, JSON.stringify($scope.issue)).success(function(data, status) {
            alert(data);
        }).error(function(data, status) {
			alert("WOLOWLO_EROR");
        });
    };

    $(function() {
		$("[data-add-issue]").click(function() {
			$scope.issue = {
				title: "ko",
				text: "dsa",
				status: "da",
				id: 0
			};
			$scope.$apply();
		});


		$("tr[data-id]").click(function() {
			$scope.issue = {
				/*postedBy: $(this).find("[data-postedBy}").html(),*/
				/*id: $(this).find("[data-id]").html()*/
				title: $(this).find("[data-title]").html(),
				text: $(this).find("[data-text]").html(),
				status: $(this).find("[data-status]").html(),
				id: $(this).attr("data-id")
			};
			
			$scope.$apply();
			$("#add-issue-modal").modal("show");
		});
	});
});
