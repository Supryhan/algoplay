package com.supryhan.problemslab.algebraicdatatypes;

/*
 * Task: Access Result Processing with ADT
 *
 * Implement the entire solution in a single Java file.
 *
 * 1. Create a sealed interface named AccessResult.
 *
 * 2. Define three record implementations:
 *    - Granted(String username)
 *    - Denied(String reason)
 *    - TemporaryFailure(String message)
 *
 * 3. Create a list containing 5-6 sample AccessResult values
 *    representing different access outcomes.
 *
 * 4. Create an enum named RetryDecision with three values:
 *    - ALLOW
 *    - DENY
 *    - RETRY
 *
 * 5. Create a Function<AccessResult, RetryDecision> policy:
 *    - Granted -> ALLOW
 *    - Denied -> DENY
 *    - TemporaryFailure -> RETRY if the message contains "timeout"
 *    - otherwise -> DENY
 *
 * 6. Implement a toResponse(AccessResult result) method.
 *    Use instanceof pattern matching to convert each AccessResult
 *    variant into a human-readable String.
 *
 * 7. Using the Stream API, obtain a List<String> containing
 *    usernames from Granted results only.
 *
 *    Implement this either with:
 *    - filter + map
 *    or
 *    - mapMulti
 *
 * 8. Using the Stream API and the retry policy, obtain a
 *    List<AccessResult> containing only results for which
 *    RetryDecision.RETRY is returned.
 *
 * 9. Print:
 *    - the converted response for at least one AccessResult
 *    - the list of granted usernames
 *    - the list of retryable results
 */
public class AccessDemoAdt {
}
