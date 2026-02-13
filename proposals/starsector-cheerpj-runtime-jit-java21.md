# Proposal: CheerpJ Runtime/JIT Improvements for Starsector + Java 21 Compatibility

## Summary
This proposal shares a real workload (Starsector on CheerpJ 4.2) that appears to stress runtime continuation/interop paths and requests:

1. Internal runtime/JIT optimizations for high-frequency JNI/graphics/event loops.
2. A Java 21 support roadmap (and migration mode) while keeping Java 8/11/17 backward compatibility stable.

## Why this workload is useful
Starsector is a long-running Java application with heavy startup reflection/classloading and sustained OpenGL/event interop.
It is useful as a stress benchmark for:

- Java<->JS bridge call overhead.
- Continuation/resume scheduling overhead.
- Negative-lookup and repeated missing-resource handling.
- Graphics swap/event polling loops where progress may stall.

## Observed signals from local runs (CheerpJ 4.2 integration)
From a watcher corpus of 159 runs:

- Final status most frequently stayed at `Graphics system is initializing`.
- `draw` remained `0` in all sampled runs while `swap`, `begin`, and poll counters increased.
- Peak run reached:
  - `totalCalls: 53,454,123`
  - `swap: 4092`
  - `begin: 1,768,278`
  - `draw: 0`
- Frequent repeated 404 probe patterns were observed in many runs (possible negative lookup churn).

These are signs that even when rendering path calls are active, the runtime spends substantial time in non-progress loops.

## Runtime/JIT improvement requests
### P0
- Aggressive monomorphic specialization for hot JNI/native bridge signatures.
- Lower-overhead continuation resume paths (`CheerpJContinue` style suspend/resume).
- Tiering policy that promotes hot poll/swap loops quickly when call shapes are stable.

### P1
- Negative lookup cache for repeated missing path/class/resource probes.
- Cheaper repeat exception handling in known-failing hot paths.
- Startup prioritization for class/resource load hot methods.

### P2
- Further optimization around memory growth + typed-view rebind paths.
- Optional persistent profile reuse across repeated runs of the same workload.

## Java 21 support + backward compatibility request
Current public docs/readme emphasize Java 8/11/17 support. It would help to have:

- A public Java 21 support roadmap/status.
- Compatibility mode guidance for mixed ecosystems (8/11/17 + 21 bytecode/libs).
- Clear fallback behavior for unsupported classfile levels and migration recommendations.
- Test matrix publication for backward compatibility regressions.

## Repro and data sharing
I can provide:

- Repro harness scripts.
- Run logs and watcher JSON artifacts.
- A trimmed benchmark scenario focused only on init/swap/poll phases.

If runtime source changes are in an internal/private repo, guidance on the preferred contribution channel would be appreciated.

## Requested next step
Please advise whether you prefer:

1. A public benchmark artifact package in this repo.
2. A dedicated issue thread with run bundles attached.
3. Private handoff for deeper runtime profiling data.
