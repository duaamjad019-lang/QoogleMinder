package com.example.data.model

object SamplePresets {

    val presets = listOf(
        SamplePreset(
            id = "swe",
            roleTitle = "Staff Platform Engineer",
            companyOrField = "Stripe / Cloud Infrastructure",
            resumeSnippet = """
Alex Morgan
San Francisco, CA | alex.morgan@email.com | github.com/alexmorgan-eng | linkedin.com/in/alexmorgan-dev

PROFESSIONAL SUMMARY
Senior Software Engineer with 7+ years building backend systems and cloud platforms. Experienced in Kotlin, Go, PostgreSQL, and Kubernetes. Worked on payment processing and microservice architecture.

EXPERIENCE
FinTech Systems Inc. — Senior Backend Engineer (2021 – Present)
- Responsible for microservice backend handling customer financial transactions.
- Wrote API services using Go and Kotlin with PostgreSQL databases.
- Helped improve database query performance and reduced server response times.
- Worked with DevOps to migrate monolithic deployment to Kubernetes clusters.
- Mentored junior engineers and participated in on-call rotation.

CloudCore Solutions — Software Engineer (2018 – 2021)
- Developed RESTful APIs for enterprise cloud customers.
- Handled Kafka event streaming pipelines for user event logs.
- Wrote automated tests using JUnit and Mockito to increase test coverage.
- Participated in sprint planning and code reviews.

SKILLS
Languages: Go, Kotlin, Java, Python, SQL, Bash
Infrastructure: AWS, Kubernetes, Docker, Terraform, Kafka, Redis, PostgreSQL, Prometheus
Practices: Microservices, Distributed Systems, CI/CD, Agile/Scrum

EDUCATION
B.S. in Computer Science — University of California, Berkeley (2018)
            """.trimIndent(),
            jdSnippet = """
Job Title: Staff Platform Engineer, Global Core Ledgers
Company: Stripe
Location: Remote / San Francisco, CA

About the Role:
We are looking for a Staff Platform Engineer to lead the architecture of our next-generation distributed transaction ledger, processing over 500 million transactions daily with 99.999% uptime guarantees. You will design fault-tolerant distributed consensus algorithms, optimize low-latency event queues, and mentor top-tier engineering talent.

Key Requirements:
- 7+ years designing and running mission-critical distributed systems at massive scale.
- Expert proficiency in Go, Java/Kotlin, or Rust with deep knowledge of memory management and concurrency.
- Demonstrated mastery of distributed ACID transactions, idempotency patterns, and event-driven architectures (Kafka, Raft, Paxos).
- Proven track record optimizing high-throughput relational & distributed stores (PostgreSQL, CockroachDB, DynamoDB).
- Experience leading architectural decisions across multi-team engineering organizations and driving zero-downtime database migrations.
- Strong cross-functional leadership, operational excellence, and disaster recovery strategies.
            """.trimIndent()
        ),
        SamplePreset(
            id = "pm",
            roleTitle = "Director of Product Management",
            companyOrField = "B2B SaaS / FinTech Scaleup",
            resumeSnippet = """
Elena Rostova
New York, NY | elena.rostova@email.com | linkedin.com/in/elenarostova-pm

SUMMARY
Product Management leader with 8 years driving B2B SaaS product roadmaps from inception to scale. Background in user analytics, customer discovery, pricing strategy, and agile product development.

WORK EXPERIENCE
SaaSFlow Technologies — Principal Product Manager (2021 – Present)
- Managed core enterprise analytics product used by Fortune 500 clients.
- Defined product roadmap and coordinated with engineering, design, and sales teams.
- Conducted regular user interviews and analyzed product telemetry data in Amplitude.
- Launched new reporting features that helped customer retention.
- Re-architected pricing tier packaging which helped grow expansion revenue.

ScaleUp Labs — Senior Product Manager (2018 – 2021)
- Led team of 10 engineers and 2 UX designers on onboarding workflow redesign.
- Reduced initial user setup friction and increased weekly active users.
- Collaborated with marketing for product launches and customer webinars.

SKILLS
Product Strategy, Roadmap Prioritization, PLG (Product-Led Growth), User Research, Amplitude, Jira, SQL, OKRs, Enterprise Pricing, Executive Stakeholder Management

EDUCATION
MBA — Columbia Business School
B.A. in Economics — NYU
            """.trimIndent(),
            jdSnippet = """
Job Title: Director of Product Management, Enterprise Platform
Company: Apex Cloud Technologies
Location: New York, NY (Hybrid)

Role Overview:
Apex is seeking a visionary Director of Product Management to own our flagship enterprise analytics platform generating ${'$'}45M+ ARR. You will set multi-year strategy, champion product-led growth (PLG) motions, overhaul enterprise monetization funnels, and lead a high-performing team of 5 Senior PMs.

Responsibilities & Qualifications:
- 8+ years of B2B SaaS product management experience with a proven history of scaling products from ${'$'}10M to ${'$'}50M+ ARR.
- Strong mastery of Product-Led Growth (PLG) mechanics, user onboarding optimization, and net revenue retention (NRR) expansion.
- Expertise in quantitative cohort analysis, customer telemetry (Amplitude/Mixpanel), and monetization/pricing model redesign.
- Experience managing, coaching, and hiring senior product managers.
- Exceptional executive presence with demonstrated ability to align C-suite stakeholders and enterprise advisory boards.
            """.trimIndent()
        ),
        SamplePreset(
            id = "data_ai",
            roleTitle = "Lead AI / ML Systems Engineer",
            companyOrField = "AI Research & Automation",
            resumeSnippet = """
Marcus Vance
Seattle, WA | marcus.vance@email.com | github.com/marcusvance-ai

SUMMARY
Machine Learning Engineer with 6 years experience developing production ML pipelines, LLM fine-tuning, and scalable inference infrastructure in Python and PyTorch.

EXPERIENCE
OmniAI Corp — Senior ML Systems Engineer (2022 – Present)
- Built internal generative AI pipelines using open source LLMs and vector embeddings.
- Reduced inference latency on GPU clusters with TensorRT and quantization.
- Implemented RAG (Retrieval-Augmented Generation) system for enterprise semantic search.
- Handled data preprocessing pipelines using Apache Spark and Ray.

DeepScale Analytics — Machine Learning Engineer (2019 – 2022)
- Trained predictive models using Scikit-Learn, XGBoost, and PyTorch.
- Created automated feature store and model evaluation benchmarks.
- Deployed models to AWS SageMaker endpoints with Docker.

SKILLS
Python, PyTorch, TensorRT, vLLM, LangChain, Milvus, Vector Databases, Docker, Kubernetes, AWS, MLflow, Ray, Triton Inference Server

EDUCATION
M.S. in Computer Science (Machine Learning) — University of Washington
            """.trimIndent(),
            jdSnippet = """
Job Title: Lead AI & Inference Systems Engineer
Company: Cerebras / HyperScale AI
Location: Seattle, WA / Remote

Job Description:
We are looking for a Lead AI Systems Engineer to architect ultra-low-latency inference serving systems for multi-billion parameter foundation models. You will spearhead high-throughput distributed serving architectures (vLLM, TensorRT-LLM), optimize speculative decoding, and deploy enterprise-grade RAG systems serving millions of daily API requests.

Requirements:
- 5+ years of software/systems engineering focused on deploying production deep learning & foundation models.
- Deep expertise in PyTorch internals, CUDA kernel optimization, model quantization (AWQ/GPTQ/FP8), and GPU memory management.
- Hands-on experience scaling inference engines (Triton, vLLM) under strict p99 latency SLA (<50ms).
- Proven track record implementing high-accuracy semantic retrieval and RAG systems with vector indexing at scale.
            """.trimIndent()
        ),
        SamplePreset(
            id = "marketing",
            roleTitle = "Head of Global Growth Marketing",
            companyOrField = "Consumer Tech / Mobile",
            resumeSnippet = """
Jordan Taylor
Austin, TX | jordan.taylor@email.com | linkedin.com/in/jordantaylor-growth

SUMMARY
Performance marketing and customer acquisition director with 9 years scaling paid acquisition, lifecycle CRM, and conversion rate optimization (CRO) for high-growth consumer apps.

EXPERIENCE
Velocity Brands — Senior Director of Performance Marketing (2021 – Present)
- Managed digital ad spend across Meta, Google Ads, TikTok, and Apple Search Ads.
- Led creative testing program and conversion rate optimization on landing pages.
- Directed lifecycle email and push notification automation via Braze.
- Worked with data engineering to build multi-touch attribution reporting.

Pulse Mobile — Growth Marketing Manager (2017 – 2021)
- Scaled paid user acquisition channels while maintaining CAC targets.
- Ran A/B tests on onboarding flows to improve 30-day user retention.
- Managed agency relationships and creative production pipeline.

SKILLS
Paid Media, Multi-Touch Attribution, CAC/LTV Modeling, Braze, Google Ads, Meta Ads Manager, SQL, Mixpanel, App Store Optimization (ASO), Retention Marketing
            """.trimIndent(),
            jdSnippet = """
Job Title: Head of Global Growth Marketing
Company: NeoFit Health
Location: Austin, TX (Hybrid)

About the Job:
NeoFit is searching for an analytical and brand-savvy Head of Global Growth Marketing to command our ${'$'}25M+ annual customer acquisition budget. You will drive sustainable CAC/LTV efficiency, expand international user acquisition, and build state-of-the-art marketing mix modeling (MMM) and attribution pipelines.

Requirements:
- 8+ years leading performance marketing, user acquisition, and lifecycle automation for top-tier consumer apps.
- Track record managing ${'$'}15M+ annual budgets while improving Blended CAC by 20%+.
- Deep expertise in post-iOS14 attribution, MMM (Robyn/LightweightMMM), incrementality testing, and App Store Optimization.
- Experience managing in-house performance marketing teams and creative sprint cycles.
            """.trimIndent()
        )
    )
}
