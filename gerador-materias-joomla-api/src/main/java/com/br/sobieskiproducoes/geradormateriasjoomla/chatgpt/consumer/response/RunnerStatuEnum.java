/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Jorge Demetrio
 * @since 10 de abr. de 2024 22:06:03
 * @version 1.0-10 de abr. de 2024
 */
public enum RunnerStatuEnum {
  QUEUED("queued"), // When Runs are first created or when you complete the required_action, they are moved to a queued status. They should almost immediately
                    // move to
          // in_progress.
  IN_PROGRESS("in_progress"), // While in_progress, the Assistant uses the model and tools to perform steps. You can view progress being made by the Run by
                              // examining the Run
               // Steps.
  INCOMPLETO("incomplete"),
  COMPLETED("completed"), // The Run successfully completed! You can now view all Messages the Assistant added to the Thread, and all the steps the Run took.
                          // You can also
             // continue the conversation by adding more user Messages to the Thread and creating another Run.
  REQUIRES_ACTION("requires_action"), // When using the Function calling tool, the Run will move to a required_action state once the model determines the names
                                      // and arguments of
                   // the functions to be called. You must then run those functions and submit the outputs before the run proceeds. If the outputs are not
                   // provided before the expires_at timestamp passes (roughly 10 mins past creation), the run will move to an expired status.
  EXPIRED("expired"), // This happens when the function calling outputs were not submitted before expires_at and the run expires. Additionally, if the runs take
                      // too long
           // to execute and go beyond the time stated in expires_at, our systems will expire the run.
  CANCELLING("cancelling"), // You can attempt to cancel an in_progress run using the Cancel Run endpoint. Once the attempt to cancel succeeds, status of the
                            // Run moves to
              // cancelled. Cancellation is attempted but not guaranteed.
  CANCELLED("cancelled"), // Run was successfully cancelled.
  FAILED("failed");// You can view the reason for the failure by looking at the last_error object in the Run. The timestamp for the failure will be recorded
                   // under
         // failed_at.


  private final String valor;

  RunnerStatuEnum(final String valorP) {
    this.valor = valorP;
  }

  @JsonValue
  @Override
  public String toString() {
    return this.valor;
  }
}
